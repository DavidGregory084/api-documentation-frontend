/*
 * Copyright 2021 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.apidocumentation.connectors

import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.apidocumentation.config.ApplicationConfig
import uk.gov.hmrc.apidocumentation.models._
import uk.gov.hmrc.apidocumentation.models.JsonFormatters._
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.bootstrap.http.HttpClient
import uk.gov.hmrc.play.http.metrics._
import uk.gov.hmrc.play.partials.HtmlPartial
import uk.gov.hmrc.play.partials.HtmlPartial.connectionExceptionsAsHtmlPartialFailure

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DeveloperFrontendConnector @Inject()(http: HttpClient, appConfig: ApplicationConfig, val apiMetrics: ApiMetrics)(implicit ec: ExecutionContext) extends RecordMetrics {

  val api = API("third-party-developer-frontend")
  private lazy val serviceBaseUrl = appConfig.developerFrontendBaseUrl

  def fetchNavLinks()(implicit hc: HeaderCarrier): Future[Seq[NavLink]] = record {
    import uk.gov.hmrc.http.HttpReads.Implicits._
    http.GET[Seq[NavLink]](s"$serviceBaseUrl/developer/user-navlinks")
  }

  def fetchTermsOfUsePartial()(implicit hc: HeaderCarrier): Future[HtmlPartial] = record {
    http.GET[HtmlPartial](s"$serviceBaseUrl/developer/partials/terms-of-use") recover connectionExceptionsAsHtmlPartialFailure
  }
}

