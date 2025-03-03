package tests;

import data.Brands;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ParametrizedTest extends TestBase {


    @ValueSource( strings = {"Kia", "Mazda", "Geely"})
    @ParameterizedTest(name = "Поиск по производителю на главной странице ValueSource")
    @Tags({
            @Tag("SMOKE"),
            @Tag("WEB")
    })
    void successfulBrandSearchTestValueSource(String searchQuery) {
        open("https://auto.ru/");
        $$(".IndexMarks__item-name").findBy(text(searchQuery)).click();
        $$(".ListingHead__title").shouldHave(texts(searchQuery));
    }

    @CsvFileSource(resources = "/Brands.csv")
    @ParameterizedTest(name = "Поиск по производителю на главной странице CsvFileSource")
    @Tags({
            @Tag("SMOKE"),
            @Tag("WEB")
    })
    void successfulBrandSearchTestCsvFileSource(String searchQuery) {
        open("https://auto.ru/");
        $$(".IndexMarks__item-name").findBy(text(searchQuery)).click();
        $$(".ListingHead__title").shouldHave(texts(searchQuery));
    }

    @EnumSource(Brands.class)
    @ParameterizedTest(name = "Поиск по производителю на главной странице EnumSource")
    @Tags({
            @Tag("SMOKE"),
            @Tag("WEB")
    })
    void successfulBrandSearchTestValueSource(Brands brand) {
        open("https://auto.ru/");
        $$(".IndexMarks__item-name").findBy(text(brand.brand)).click();
        $$(".ListingHead__title").shouldHave(texts(brand.brand));
    }

}
