import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageOutputStream

/**
 * Created by gmateo on 03.12.15.
 */
fun main(args: Array<String>) {
    println("Hello World, we're writing this in java bitches")


    if (args.size > 0) {
        // grab the output image type from the first image in the sequence
        val path = args[0]
        val fileCount = File(path).listFiles().count()
        println(path)
        val firstImage = ImageIO.read(File(path + "/pitch0.jpg"))
        val output = FileImageOutputStream(File(path + "/output.gif"))

        // create a gif sequence with the type of the first image, 1 second
        // between frames, which loops continuously
        val writer = GifSequenceWriter(output, firstImage.type, 1, true)
        writer.writeToSequence(firstImage)
        for(i in 0..fileCount-2){
            println("$i/$fileCount")
            val filePath = path + "/pitch$i.jpg"
            val nextImage = ImageIO.read(File(filePath))
            writer.writeToSequence(nextImage)
        }

        for(i in 0..fileCount-2){
            println("$i/$fileCount")
            val filePath = File(path + "/pitch$i.jpg")
            Files.delete(filePath.toPath())
        }
        // write out the first image to our sequence...


        writer.close()
        output.close()
    } else {
        println("Usage: java GifSequenceWriter [list of gif files] [output file]")
    }

}