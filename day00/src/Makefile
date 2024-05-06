TARGETS = ex00 ex01 ex02 ex03 ex04 ex05

all: $(TARGETS)

$(TARGETS): clean
	@javac $@/Program.java
	@cd $@ && java Program

clean:
	@for dir in $(TARGETS); do \
		rm -rf $$dir/Program.class; \
	done
