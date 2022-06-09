import org.openrndr.WindowMultisample
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.isolated
import org.openrndr.extra.noise.Random
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.extra.shapes.grid
import org.openrndr.math.Vector2

// Based on http://recodeproject.com/artwork/v2n3boxes-i
fun main() = application {
    configure {
        width = 720
        height = 720
        multisample = WindowMultisample.SampleCount(8)
    }
    oliveProgram {
        val maxRot = 90.0
        val maxOff = 75.0
        val grid = drawer.bounds.grid(
            10, 10,
            100.0, 100.0,
            -2.0, -2.0
        ).flatten()
        extend {
            drawer.clear(ColorRGBa.WHITE)
            drawer.fill = ColorRGBa.WHITE.opacify(0.8)
            val maxDist = drawer.bounds.center.length
            Random.isolated {
                grid.forEach {
                    val distToInterestingPoint =
                        it.center.distanceTo(mouse.position) / maxDist
                    val strength =
                        (1.0 - distToInterestingPoint - 0.35).coerceAtLeast(0.0) * 0.5

                    drawer.isolated {
                        drawer.translate(it.center)
                        drawer.rotate(strength * Random.double(-maxRot, maxRot))
                        //drawer.rotate(strength * Random.simplex(it.center * 0.005) * maxRot)
                        drawer.translate(-it.dimensions * 0.5)
                        val x = Random.simplex(it.center * 0.33) * maxOff * strength
                        val y = Random.simplex(it.center.yx * 0.33) * maxOff * strength
                        drawer.translate(x, y)
                        drawer.rectangle(Vector2.ZERO, it.width, it.height)
                    }
                }
            }
        }
    }
}
