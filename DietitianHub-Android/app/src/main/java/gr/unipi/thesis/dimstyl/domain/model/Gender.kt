package gr.unipi.thesis.dimstyl.domain.model

enum class Gender {

    MALE, FEMALE;

    override fun toString(): String = this.name[0] + this.name.substring(1).lowercase()

}