# Java Process Plus




## Installation
### Option 1) From Maven Central (recommended)
* Add maven dependency
    * ```
      <dependency>
          <groupId>com.mgustran</groupId>
          <artifactId>java-process-plus</artifactId>
          <version>1.0.1</version>
      </dependency>
      ```

### Option 2) Jitpack
You can use jitpack to add this dependency:
  * Add jitpack repository:
    * ```
      <repositories>
         <repository>
             <id>jitpack.io</id>
             <url>https://jitpack.io</url>
         </repository>
      </repositories> 
      ```
  * Add maven dependency:
    * ```
      <dependency>
          <groupId>com.github.mgustran</groupId>
          <artifactId>java-process-plus</artifactId>
          <version>1.0.1</version>
      <dependency>
      ```

### Option 3) Local
* Download project and install the lib to your local:
  * `mvn clean install`
  * add dependency to pom.xml (like in option 1)


## Usage

* To use the different methods see the tests under `src/test/java/com/mgustran/jpp`

[//]: # (* todo: add examples into README)

## About the lib

* This library provides some utilities to manage Process, ProcessBuilder and its output.

* Has void blocking method, void non-blocking method and string/list<string> return methods 

* This library works with **Java >= 8**, **Maven** and has some dependencies already provided:
  * lombok : 1.18.26
  * junit-jupiter (test) : 5.9.1


## Contributing
You are invited to participate and contribute to the improvement of this lib, but i want to stay clear about some things:

I tried to make this project not dependent on many libs, to be easily integrated with other different projects, so..:
* I don't want to put more deps on project, unless they optimize significantly something

[//]: # (## License)

[//]: # (* see LICENSE.txt)



[<img src="https://media.tenor.com/Qk9SE5aOLPEAAAAM/yes-awkward.gif">](https://www.youtube.com/watch?v=bxaYsGo-Sec)

