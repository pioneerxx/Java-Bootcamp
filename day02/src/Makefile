TARGETS = ex00 ex01 ex02

all: $(TARGETS)

$(TARGETS): clean
	javac $@/*.java
	java $@.Program

clean:
	@for dir in $(TARGETS); do \
		rm -rf $$dir/*.class; \
	done
