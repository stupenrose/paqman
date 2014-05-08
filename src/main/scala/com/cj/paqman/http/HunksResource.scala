package com.cj.paqman.http

import org.httpobjects._
import org.httpobjects.DSL._
import com.cj.paqman.Data
import com.cj.paqman.Session
import java.util.UUID
import com.cj.paqman.Paqman
import com.cj.paqman.AuthRequest
import com.cj.paqman.AuthMechanism
import com.cj.paqman.Service
import com.cj.paqman.PersonStatus
import com.cj.paqman.Jackson
import com.cj.paqman.QualSummary
import com.cj.paqman.Qual
import com.cj.paqman.Qual
import com.cj.paqman.Hunk

class HunksResource (datas:Data) extends HttpObject("/api/quals/{id}/hunks"){
          
  override def post(r:Request) = {
    val id = r.path().valueFor("id")
    val maybeQual = datas.qualifications.get(id)
    maybeQual match {
      case Some(qual) => {
        val newHunk = Jackson.readJson[Hunk](r.representation()).copy(id=UUID.randomUUID().toString())
        
        val updatedQual = qual.copy(hunks = (qual.hunks :+ newHunk))
        datas.qualifications.put(id, updatedQual);
        CREATED(Location(s"/api/quals/${id}/hunks/${newHunk.id}"))
      }
      case None => NOT_FOUND
    }
  }
}