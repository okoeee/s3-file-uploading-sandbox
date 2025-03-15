object Main extends App {

  val credentialOpt = sys.env.get("MY_ENV_VAR")

  for {
    credential <- credentialOpt
  } yield {
    println(s"Credential: $credential")
  }

}
