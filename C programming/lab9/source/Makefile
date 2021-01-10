CC = gcc
OBJS = scheduling.o prio.o srt.o fcfs.o rr.o
DEPS = scheduling.h
CFLAGS = -g -Wall
LIBRARIES = -lrt -lpthread

all: scheduling

scheduling: $(OBJS) $(DEPS)
	$(CC) $(LIBRARIES) $(CFLAGS) -o $@ $^

.o: %.c $(DEPS)
	$(CC) $(LIBRARIES) $(CFLAGS)-o $@ -c $<

clean:
	rm -rf *.o *.out scheduling
