import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.Random
import org.openrndr.extra.shapes.grid

// Based on http://recodeproject.com/artwork/v3n2untitled-3-schneeberger
fun main() = application {
    configure {
        width = 800
        height = 800
    }
    program {
        val grid = drawer.bounds.grid(10, 10, 50.0, 50.0).flatten()
        val lines = grid.map {
            Random.pick(it.contour.segments, count = Random.int0(2))
        }.flatten()
        extend {
            Random.isolated {
                drawer.clear(ColorRGBa.WHITE)
                lines.forEach {
                    drawer.strokeWeight = int(1, 5).toDouble()
                    drawer.segment(it)
                }
            }
        }
    }
}
