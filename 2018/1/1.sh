#!/usr/bin/env bash
#Solution for https://adventofcode.com/2018/day/1
#
#Usage:
#  $ ./1.sh

set -euo pipefail
IFS=$'\n\t'

awk '{for(i=1;i<=NF;i++)s+=$i}END{print s}' input.txt
