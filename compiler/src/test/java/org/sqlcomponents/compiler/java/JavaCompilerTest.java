package org.sqlcomponents.compiler.java;

import org.junit.jupiter.api.Test;
import org.sqlcomponents.core.crawler.Crawler;
import org.sqlcomponents.core.exception.ScubeException;
import org.sqlcomponents.core.mapper.Mapper;
import org.sqlcomponents.core.mapper.java.JavaOrmMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

class JavaCompilerTest {
    @Test
    void writeCode() throws ScubeException, SQLException, IOException {
        new Application()
                .connectToPostgress()
                .understand()
                .mapToJava()
                .writeCode();
    }

    class Application {

        private final org.sqlcomponents.core.model.Application application;

        Application() {
            this.application = new org.sqlcomponents.core.model.Application();
        }

        Application connectToPostgress() throws SQLException, ScubeException {
            application.setName("Movie");
            application.setUrl("jdbc:postgresql://localhost:5432/moviedb");
            application.setUserName("moviedb");
            application.setPassword("moviedb");
            application.setSchemaName("moviedb");
            // daoProject.setTablePatterns(Arrays.asList("movie"));

            application.setOnline(true);
            application.setBeanIdentifier("model");
            application.setDaoIdentifier("store");
            application.setDaoSuffix("");
            application.setRootPackage("org.example");
            application.setCleanSource(true);


            Crawler crawler = new Crawler();
            Mapper mapper = new JavaOrmMapper();
            application.setOrm(mapper.getOrm(application,crawler));
            return this;
        }

        Application understand() {

            application.setMethodSpecification(Arrays.asList(
                    "DeleteByEntity"
                    , "DeleteByPK"
                    , "GetAll"
                    , "GetByEntity"
                    , "GetByPK"
                    , "GetByPKExceptHighest"
                    , "GetByPKUniqueKeys"
                    , "InsertByEntiy"
                    , "IsExisting"
                    , "MViewRefresh"
                    , "UpdateByPK"
            ));
            return this;
        }

        Application mapToJava() {
            return this;
        }

        void writeCode() throws IOException {
            application.setSrcFolder("../java-test/target/generated-sources/movie-db");
            application.compile(new JavaCompiler());
            System.out.println("Granted !");
        }

    }
}