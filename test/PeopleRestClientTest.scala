import external.people.client.PeopleClient
import org.scalatestplus.play.PlaySpec
import play.api.test.WsTestClient

import scala.concurrent.Await
import scala.concurrent.duration._

class PeopleRestClientTest extends PlaySpec {

  import scala.concurrent.ExecutionContext.Implicits.global

  "person rest client" should {
    /*"pass getting person with id 1" in {
      WsTestClient.withClient { client =>
        val result = Await.result(new PeopleClient(client, global).getPerson("1"), 10.seconds)
        result. must_== Seq("octocat/Hello-World")
      }
    }*/
  }

}
