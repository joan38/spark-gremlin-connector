package com.goyeau.spark.titan

import org.apache.spark.SparkContext
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal

import scala.reflect.ClassTag

/**
  * Created by Joan on 13/03/2016.
  */
package object connector {
  
  implicit class SparkContextFunctions[E: ClassTag](traversal: GraphTraversal[_, E]) {
    def toRDD(numSlice: Int = 0)(implicit sc: SparkContext) = new TitanRDD(sc, traversal, numSlice)
  }
  
}
