import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Prueba : DescribeSpec( {

        describe("Test") {

            it("sumar") {
                //Act
                suma.sumar()
                //Assert
                suma.resultado().shouldBe(4)
            }

}
})

