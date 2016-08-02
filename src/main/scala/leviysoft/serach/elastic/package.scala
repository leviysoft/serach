package leviysoft.serach

import com.sksamuel.elastic4s.{HitAs, RichSearchHit}
import play.api.libs.json.{Json, Reads}

package object elastic {
  implicit def playJsonHitAs[T](implicit r: Reads[T]) = new HitAs[T] {
    override def as(hit: RichSearchHit): T = Json.parse(hit.sourceAsString).as[T]
  }
}
