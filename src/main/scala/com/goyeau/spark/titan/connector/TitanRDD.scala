package com.goyeau.spark.titan.connector

import com.goyeau.spark.gremlin.connector.Workaround._
import com.thinkaurelius.titan.core.{TitanFactory, TitanGraph}
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkContext, TaskContext}
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal

import scala.collection.JavaConversions._
import scala.reflect.ClassTag

/**
  * Created by Joan on 05/02/2016.
  */
class TitanRDD[E: ClassTag](sc: SparkContext,
                            traversal: GraphTraversal[_, E],
                            numSlices: Int = 0) extends RDD[E](sc, Nil) {
  private val defaultParallelism = sc.defaultParallelism
  private val readConf = GraphConf(traversal.asAdmin.getGraph.get.asInstanceOf[TitanGraph].configuration)
  
  override def compute(split: Partition, context: TaskContext): Iterator[E] = {
    val partition = split.asInstanceOf[TitanPartition[E]]
    val partitionTraversal = partition.traversal.asAdmin
    val graph = TitanFactory.open(readConf.toTitanConf)
    partitionTraversal.setGraph(graph)
    partitionTraversal.toList().toIterator
  }

  override protected def getPartitions: Array[Partition] = {
    val numElement = cloneTraversal(traversal).count().toList().head
    val numPartitions =
      if (numSlices > 0 && numElement >= numSlices) numSlices
      else if (numElement >= defaultParallelism) defaultParallelism
      else numElement.toInt
    val partitionSize = numElement / numPartitions
    
    (0 until numPartitions).toArray map { i =>
      val from = partitionSize * i
      val to = partitionSize * (i + 1)
      val partitionTraversal = cloneTraversal(traversal).range(from, to)
      TitanPartition(i, partitionTraversal)
    }
  }
}

