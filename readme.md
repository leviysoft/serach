# Serach
Serach is a GUI ElasticSearch client written in Scala that uses [elastic4s](https://github.com/sksamuel/elastic4s) DSL.

"Serach" is a common typo and (of course) mean "search".

At this moment Serach can execute Your query and represent result in a TreeView. There will be more
debug & administration features later.

#Building serach
Serach uses [sbt-javafx](https://github.com/kavedaa/sbt-javafx) for packaging. You can bundle Serach by the
following steps:
- set proper path for `ant-javafx.jar` in build.sbt
- set desired `bundleType` in build.sbt, see [this](https://github.com/kavedaa/sbt-javafx/blob/master/doc/packaging.md) for details
- run `javafx-prepare-build` to prepare build.xml for ant-javafx (this is necessary to be explicitly executed due to a bug in sbt-javafx)
- run `package-javafx`
