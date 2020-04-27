JAVAC=/usr/bin/javac
.SUFFIXES: .java .class

SRCDIR=src
BINDIR=bin
DOCDIR=doc

default:
	$(JAVAC) -Xlint:unchecked -d $(BINDIR)/ $(SRCDIR)/*.java

clean:
	rm $(BINDIR)/*.class
	rm -Rf doc

doc:
	javadoc -d$(DOCDIR) $(SRCDIR)/*.java

run:
	java -cp $(BINDIR) Control

test:
	java -Xmx16g -cp $(BINDIR) Test 196.24.154.59
