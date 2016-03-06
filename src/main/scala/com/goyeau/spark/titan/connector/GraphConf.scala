package com.goyeau.spark.titan.connector

import org.apache.commons.configuration.{BaseConfiguration, Configuration}

import scala.collection.JavaConversions._

/**
  * Created by Joan on 10/03/2016.
  */
object GraphConf {
  def apply(titanConf: Configuration) =
    new GraphConf((titanConf.getKeys map (k => k -> titanConf.getProperty(k))).toMap)
}

class GraphConf(map: Map[String, Any]) extends Map[String, Any] with Serializable {
    
  override def get(key: String): Option[Any] = map.get(key)
  
  override def iterator: Iterator[(String, Any)] = map.iterator

  override def +[B1 >: Any](kv: (String, B1)): GraphConf = new GraphConf(map + kv)

  override def -(key: String): GraphConf = new GraphConf(map - key)
  
  def toTitanConf: Configuration = {
    val graphConf = new BaseConfiguration()
    map foreach { case (k, v) => graphConf.setProperty(k, v) }
    graphConf
  }
}