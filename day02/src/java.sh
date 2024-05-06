#!/bin/bash

if [ $# -ne 2 ]; then
    echo "Usage: $0 <number of directories to create> <day number>"
    exit 1
fi

num_of_dirs="$1"

targets="ex00"
for i in $(seq -f "%02g" 1 $num_of_dirs); do
    targets="$targets ex${i}"
done

if [ $2 -lt 10 ]; then
    day="0$2"
else
    day="$2"
fi

for i in $(seq -f "%02g" 0 $num_of_dirs); do
    mkdir ex${i}
    touch ex${i}/Program.java
done

cat >Makefile <<EOL
TARGETS = $targets

all: \$(TARGETS)

\$(TARGETS): clean
	javac \$@/*.java
	java \$@.Program

clean:
	@for dir in \$(TARGETS); do \\
		rm -rf \$\$dir/*.class; \\
	done
EOL