
# Author's notes

I have choosen to implement the "Merchant's Guide to the Galaxy" problem

## Coding environment, compiling, running

### Environment

The project is a very simple standard Maven project. Once I generated it with `mvn` command I imported the project into Eclipse (Mars for JavaEE) and edited there. Therefore in the .zip you might also find an Eclipse .project but of course the source should be correctly handled by `mvn` as well.

### Compiling the code

With Maven - you will need Maven installed to compile this project. I was using Apache Maven 3.3.9. You can find a quick guide with links here: [Maven in 5 minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

Assuming Maven binaries are available on your Path you need to enter to the project directory (where .zip was extracted to) and:
 
    > cd <project dir>
    > mvn package 

This will compile the code and also run the tests - should end up with BUILD SUCCESS message...

### Running the app

**First of all please note:**  
I have placed `testInput.txt` in the project root folder containing the test input which was given in the problem description!

#### Simplest - with Maven

Drawback is that Maven also print lines to the std out ... (Program output will be visible ~ at the middle of the output)  
But it is easy to do it so:

    > cd <project dir>
    > mvn exec:java -Dexec.mainClass="com.thoughtworks.challenge.app.MerchantsGuideApp" -Dexec.args="testInput.txt"

#### More advanced - with run script

`pom.xml` in the project is setup the way it generates a `classpath.txt` file during compile phase to the root folder. This file can be used to easily add all inculded dependencies to the CLASSPATH. Please note you also need to add the compiled .jar file (named `thoughtworks-challenge-1.0-SNAPSHOT.jar` by default by `pom.xml`).

To make life easier normaly I add a `run.bat` (for Windows) and/or `run.sh` (Unix) files to my project root folder - this time **I removed them for now** because they are forbidden being there...

E.g. my simple `run.bat` (I'm working on Windows) would look like this:

    @ECHO OFF
    for /f "delims=" %%x in (classpath.txt) do set CPATH=%%x
    java -classpath %CPATH%;target\thoughtworks-challenge-1.0-SNAPSHOT.jar %*
    
And running an appropriate class (main method) from the project would be easy, in our case:

    > cd <project dir>
    > run com.thoughtworks.challenge.app.MerchantsGuideApp testInput.txt
   
## Concept of coding and app design

### Classes

My code has 4 major parts - also reflected by packages:
   * **com.thoughtworks.challenge.romannumber**  
Classes to work with Roman numbers - as the whole problem is around Roman numbers
   * **com.thoughtworks.challenge.interpreter**  
Classes which are responsible for parsing the input. It might worth mentioning here that I designed this piece being extendable: creating / adding more Commands should be easy with this design. The Commands are in a separate sub-package **com.thoughtworks.challenge.interpreter.commands**
   * **com.thoughtworks.challenge.util**  
Just util classes making life easier (since we are not allowed using external libs now... ;-) )
   * **com.thoughtworks.challenge.app**  
Contains only the application class which is runnable from command line - this is acting as a command line wrapper around the com.thoughtworks.challenge.interpreter.CommandInterpreter which is running the show

**Please note:** I have added Javadoc to the classes and important methods.

### Using some Java 8 elements

We have just started to use Java8 recently - a couple of months ago.

I tried to balance a bit btw old style coding and new approaches introduced by Java 8 e.g. lambdas and stream based approaches. Actually I like it a lot because this approach might make the code much more simple and readable (however it is also possible to mess things up especially error handling... we have seen good and bad and very bad examples @ Morgan Stanley already... :-) ) 

### Error handling strategy

We handle only expectable exceptions in the code (e.g. IllegalArgumentException which might be thrown by RomanNumber objects during instantiation) intentionaly. These errors are handled and formatted into user readable errors (as problem description asked for by saying: "You are expected to handle invalid queries appropriately.")

In general: It is essential not to catch any other Exceptions! These runtime errors should always bubble up because normally high level processing logic might want to e.g. roll back transaction if any unexpected happened...  
