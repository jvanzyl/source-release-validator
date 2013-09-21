# Source Release Validator

This tool, for the moment, is a source archive validator for Apache Maven core releases.

With the inputs of a staging repository URL, groupId, artifactId, and version we validate the following:

- the published sha1 of the source archive matches the calculated sha1
- the published sha1 of the binary archive matches the calculated sha1
- that each file that is present in the source archive exists in the originating Git revision 
- that each file that is present in the source archive matches the sha1 of the file in the originating Git revision

A sample run looks like the following:

```
Analyzing...

stagingUrl: https://repository.apache.org/content/repositories/maven-065
groupId: org.apache.maven
artifactId: apache-maven
version: 3.1.1

Source ZIP url exists.
https://repository.apache.org/content/repositories/maven-065/org/apache/maven/apache-maven/3.1.1/apache-maven-3.1.1-src.zip

Source ZIP SHA1 url exists.
https://repository.apache.org/content/repositories/maven-065/org/apache/maven/apache-maven/3.1.1/apache-maven-3.1.1-src.zip.sha1

Binary ZIP url exists.
https://repository.apache.org/content/repositories/maven-065/org/apache/maven/apache-maven/3.1.1/apache-maven-3.1.1-bin.zip

Binary ZIP SHA1 url exists.
https://repository.apache.org/content/repositories/maven-065/org/apache/maven/apache-maven/3.1.1/apache-maven-3.1.1-bin.zip.sha1

Calculated SHA1 of source ZIP matches published SHA1 of source ZIP.
2251357aa47129674df578e787504b72cd57ed4d

Calculated SHA1 of binary ZIP matches published SHA1 of binary ZIP.
8416a8f07f9bd36bbc775eaddda0693a35937fe9

Git revision of release as determined from maven-core-3.1.1.jar:org/apache/maven/messages/build.properties(buildNumber):
0728685237757ffbf44136acec0402957f723d9a

Files that are present in the source distribution but not in the source revision:
DEPENDENCIES
```
