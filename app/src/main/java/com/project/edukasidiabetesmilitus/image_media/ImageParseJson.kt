package com.project.edukasidiabetesmilitus.image_media
import com.project.edukasidiabetesmilitus.video_media.VideoActivity
import com.project.edukasidiabetesmilitus.video_media.VideoModel
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

    fun parseJsonToVideo(context: VideoActivity, videoList: ArrayList<VideoModel>) {
        val jsonData = context.resources.openRawResource(
            context.resources.getIdentifier(
                "video",
                "raw", context.packageName
            )
        ).bufferedReader().use { it.readText() }

        val outputJsonString = JSONArray(jsonData)

        for(i in 0 until outputJsonString.length()) {
            val video = outputJsonString.getJSONObject(i)

            val videoName = video.get("name")
            val videoImage = video.get("image")
            val videoDuration = video.get("duration")
            val videoLink = video.get("link")
            val model = VideoModel()
            model.image = "" + videoImage
            model.name = "" + videoName
            model.duration = "" + videoDuration
            model.link = "" + videoLink

            videoList.add(model)
        }

    }

}