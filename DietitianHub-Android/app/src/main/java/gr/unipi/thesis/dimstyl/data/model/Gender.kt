package gr.unipi.thesis.dimstyl.data.model

enum class Gender {

    MALE, FEMALE;

    override fun toString(): String {
        return this.name[0] + this.name.substring(1).lowercase()
    }

}