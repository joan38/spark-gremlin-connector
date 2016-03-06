package com.goyeau.spark.titan.connector

import java.io.File

import com.goyeau.spark.gremlin.connector.Workaround._
import com.thinkaurelius.titan.example.GraphOfTheGodsFactory
import org.apache.commons.io.FileUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

import scala.collection.JavaConversions._

/**
  * Created by Joan on 06/02/2016.
  */
class RDDTests extends WordSpec with Matchers with BeforeAndAfterAll {

  val graphDir = "target/titangraph"
  FileUtils.deleteDirectory(new File(graphDir))
  val graph = GraphOfTheGodsFactory.create(graphDir)
  
  val conf = new SparkConf().setMaster("local").setAppName("Test")
  implicit val sc = new SparkContext(conf)

  "TitanRDD" should {

    "have the same count" in {
      val traversal = graph.traversal.V().values[String]("name")
      val expected = cloneTraversal(traversal).count().toList().head

      traversal.toRDD().count() shouldBe expected
    }

    "have the same elements" in {
      val traversal = graph.traversal.V().values[String]("name")
      val expected = cloneTraversal(traversal).toArray

      traversal.toRDD().collect() shouldBe expected
    }
  }
  
  override def afterAll() = graph.close()
}