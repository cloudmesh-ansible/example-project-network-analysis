#!/usr/bin/env bash

NA_METHOD="lognormal"              ### REGEXP REPLACE
NA_METHOD_ARGS="-n 1000"           ### REGEXP REPLACE
NA_NODESFILE="nodes.$NA_METHOD"    ### REGEXP REPLACE
NA_EDGESFILE="edges.$NA_METHOD"    ### REGEXP REPLACE
NA_METRIC="pagerank"               ### REGEXP REPLACE

SCALA_VERSION=2.10

SPARK_SUBMIT=(
    "spark-submit"
    "--deploy-mode" "cluster"
    "--master" "yarn"
    "--class" "networkanalysis"
    "target/scala-$SCALA_VERSION/Network-assembly-1.0.jar"
    "-N" "$NA_NODESFILE"
    "-E" "$NA_EDGESFILE"
)


generate() {
    ${SPARK_SUBMIT[@]} \
        -a generate \
        -g $NA_METHOD \
        $NA_ARGS
}

run() {
    ${SPARK_SUBMIT[@]} \
        -a run \
        -m $NA_METRIC
}


usage() {
    cat <<EOU
Usage: $0 <SWITCH>

where <SWITCH> is one of:
   generate - generate the dataset
   run      - run the analysis on the dataset
EOU
}


case $1 in
    "generate")
        generate;;
    "run")
        run;;
    *)
        usage
        exit 1;;
esac
