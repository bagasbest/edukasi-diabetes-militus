package com.project.edukasidiabetesmilitus.image_media
import org.json.JSONArray

object ImageParseJson {

    fun parseJsonToImage(context: ImageActivity, imageList: ArrayList<ImageModel>) {
        val jsonData = context.resources.openRawResource(
            context.resources.getIdentifier(
                "poster",
                "raw", context.packageName
            )
        ).bufferedReader().use { it.readText() }

        val outputJsonString = JSONArray(jsonData)

        for(i in 0 until outputJsonString.length()) {
            val poster = outputJsonString.getJSONObject(i)

            val posterName = poster.get("name")
            val posterImage = poster.get("image")
            val model = ImageModel()
            model.image = "" + posterImage
            model.name = "" + posterName

            imageList.add(model)
        }

    }

}