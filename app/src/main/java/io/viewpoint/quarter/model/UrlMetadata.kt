package io.viewpoint.quarter.model

import io.viewpoint.quarter.extensions.isWebUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Node

class UrlMetadata(
    val title: String?,
    val metaData: Map<String, String>
) {
    val description: CharSequence?
        get() = metaData["description"] ?: title

    val imageUrl: CharSequence?
        get() = metaData["og:image"]

    companion object {
        suspend fun from(charSequence: CharSequence?): UrlMetadata? {
            if (charSequence?.isWebUrl == false) {
                return null
            }
            return withContext(Dispatchers.IO) {
                val document = try {
                    Jsoup.connect(charSequence.toString()).get()
                } catch (t: Throwable) {
                    return@withContext null
                }
                val titleTag = document.getElementsByTag("title").getOrNull(0)
                val metaTags = document.getElementsByTag("meta")
                    .filter {
                        (it.hasAttr("name") || it.hasAttr("property")) && it.hasAttr("content")
                    }
                UrlMetadata(
                    title = titleTag?.text(),
                    metaData = metaTags.associate {
                        (it.attrOrNull("name") ?: it.attr("property")) to it.attr("content")
                    }
                )
            }
        }

        private fun Node.attrOrNull(key: String): String? = attr(key)?.takeIf {
            it.isNotEmpty()
        }
    }
}