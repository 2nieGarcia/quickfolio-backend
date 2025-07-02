## For backends
# Make sure you HAVE .env file.

In PowerShell, run this line of code:

Get-Content .env | ForEach-Object {
    if ($_ -match "^\s*([^#][^=]*)=(.*)$") {
        [System.Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim(), "Process")
    }
}

After that, run:

mvn spring-boot:run 

OR, if you don't have Maven installed, run:

.\mvnw.cmd spring-boot:run