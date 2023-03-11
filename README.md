# Multi-thread polling Spring Boot Application

### Assignment description

Implement a multithreaded service on SpringBoot, which receives a list of URLs from a specific file and in several threads reads the URLs content and writes all data from all threads into one large XML file.

The number of threads is specified in the application settings.

XML file structure:

    <Data>
        <item>
            <url></url>
            <item_data></item_data>
        </item>
    </Data>

### Install and Run
    mvn clean install
    mvn spring-boot:run
