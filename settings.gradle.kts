
rootProject.name = "kotlinbukkitkit"
include("architecture")
include("messages")
include("extensions")
include("serialization")
include("genref")
include("messages")
include("commands")
include("commands:test")
findProject(":commands:test")?.name = "test"
