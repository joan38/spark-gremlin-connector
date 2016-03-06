package com.goyeau.spark.titan.connector

import org.apache.spark.Partition
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal
import scala.language.existentials

/**
  * Created by Joan on 08/03/2016.
  */
case class TitanPartition[E](index: Int, traversal: GraphTraversal[_, E]) extends Partition
