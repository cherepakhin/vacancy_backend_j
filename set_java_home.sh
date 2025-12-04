#!/bin/bash
# в терминале можно просто export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64
# для выполнения из скрипта выполнить: source set_java_home.sh
echo $JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64
echo $JAVA_HOME