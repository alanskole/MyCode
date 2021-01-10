#!/bin/bash
if [ $# -eq 2 ]
then
cp $1 $2
lines=`wc -l < $1`
echo "The file $1 has $lines lines"
exit 0
else
echo "Usage: ./copy.sh <source_file> <dest_file>"
exit 1
fi
