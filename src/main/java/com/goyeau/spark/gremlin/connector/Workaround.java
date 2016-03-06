package com.goyeau.spark.gremlin.connector;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;

/**
 * Created by Joan on 07/02/2016.
 */
public class Workaround {
    public static <E> GraphTraversal<?, E> cloneTraversal(GraphTraversal<?, E> traversal) {
        return traversal.asAdmin().clone();
    }
}
