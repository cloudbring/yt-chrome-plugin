import chrome._
import chrome.permissions.Permission
import chrome.permissions.Permission.API
import net.lullabyte.{Chrome,ChromeSbtPlugin}


lazy val root = project.in(file("."))
    .enablePlugins(ChromeSbtPlugin)
    .settings(
      name := "yt-chrome-plugin",
      version := "0.0.1",
      scalaVersion := "2.12.1",
      scalacOptions ++= Seq(
        "-language:implictConversions",
        "-language:existentials",
        "-Xlint",
        "-depreciation",
        "-Xfata-warmings",
        "-feature"
      ),
      persistLauncher := true,
      persistLauncher in Test := false,
      relativeSourceMaps := true,
      skip in packageJSDependencies := false,
      chromeManifest := new AppManifest {
        val name: String = Keys.name.value
        val version: String = Keys.version.value
        val app = App(
          background = Background(
            scripts = Chrome.defaultScripts
          )
        )
        override val defaultLocale = Some("en")
        override val icons: Map[Int, String] = Chrome.icons(
          "icons",
          "app.png",
          Set(256)
        )
        override val permissions: Set[Permission] = Set[Permission](
          API.System.CPU,
          API.System.Display,
          API.System.Memory,
          API.System.Network,
          API.Storage
        )
      },
      libraryDependencies ++= Seq(
        "net.lullabyte" %%% "scala-js-chrome" % "0.4.0" withSources() withJavadoc()
      )
    )


