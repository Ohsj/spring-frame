package kr.co.osj4532.util

import com.lowagie.text.DocumentException
import com.lowagie.text.pdf.PdfCopy
import com.lowagie.text.pdf.PdfImportedPage
import com.lowagie.text.pdf.PdfReader
import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.*
import kotlin.jvm.Throws

/**
 * 201210 | osj4532 | created
 */
@Component
class PdfUtils {
    companion object {

        @Throws(FileNotFoundException::class)
        fun makePdfOne(htmlFilePath: String): ByteArray? {
            val file = ResourceUtils.getFile(htmlFilePath)
            return makePdfOne(file)
        }

        @Throws(IOException::class)
        fun makePdfOne(htmlFile: File): ByteArray? {
            val htmlString = Jsoup.parse(htmlFile, "UTF-8").html()
            val xhtml = htmlToXhtml(htmlString)
            return if (xhtml.isNotEmpty()) xhtmlToPdfByteArray(xhtml)
                   else null
        }

        @Throws(IOException::class)
        fun mergePdfToByteArray(byteArrayList: List<ByteArray?>): ByteArray? {
            val file = mergePdfToFile(byteArrayList)
            return if (file != null) FileUtils.readFileToByteArray(file)
                   else null
        }

        @Throws(DocumentException::class, IOException::class)
        fun mergePdfToFile(byteArrayList: List<ByteArray?>): File? {
            val file = File("merge")
            val doc = com.lowagie.text.Document()
            val copy = PdfCopy(doc, file.outputStream())
            doc.open()

            byteArrayList.forEach {
                if (it != null) {
                    val pdfReader = PdfReader(it)

                    val pageNum = pdfReader.numberOfPages
                    var page: PdfImportedPage

                    for (i in 1..pageNum) {
                        page = copy.getImportedPage(pdfReader, i)
                        copy.addPage(page)
                    }
                    copy.freeReader(pdfReader)
                }
            }
            copy.close()
            doc.close()

            return if (file.isFile && file.canRead() && file.length() > 0) file
                   else null
        }

        @Throws(IOException::class)
        private fun htmlToXhtml(html: String): String {
            val document = Jsoup.parse(html)
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml)
            return document.html()
        }

        @Throws(IOException::class)
        private fun xhtmlToPdfByteArray(xhtml: String): ByteArray? {
            val file = xhtmlToPdfFile(xhtml)
            return if (file != null) FileUtils.readFileToByteArray(file)
                   else null
        }

        @Throws(IOException::class)
        private fun xhtmlToPdfFile(xhtml: String): File? {
            val file = File("pdf")
            val renderer = ITextRenderer()
            renderer.setDocumentFromString(xhtml)
            renderer.layout()

            val os = BufferedOutputStream(file.outputStream())
            renderer.createPDF(os)

            return if (file.isFile && file.canRead() && file.length() > 0) file
                   else null
        }
    }
}