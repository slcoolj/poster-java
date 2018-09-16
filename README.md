# Poster
An extremely simple Java program for sending POST data to a web page. Supports HTTP proxies.

# Building
There is an included Gradle build script, run this to build:

    ./gradlew build

The output will be in ./build/libs/

# Usage
Create an instance of Poster with your arguments adjusted accordingly.

    Poster posterExample = new Poster("http://wow.net", "argument1=test&argument2=lol");
    Poster posterExampleProxy = new Poster("http://wow.net", "argument1=test&argument2=lol", "10.0.0.0", "8080");
After instantiation, use the function Poster#post to send.               

    posterExample.sendPost();

To get the reply, use the function Poster#getReply 

    String[] reply = posterExample.getReply();
