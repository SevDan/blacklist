package com.vk.api.sdk.objects.prettycards;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.vk.api.sdk.objects.Validable;
import com.vk.api.sdk.objects.annotations.Required;
import com.vk.api.sdk.objects.base.Image;

import java.util.List;
import java.util.Objects;

/**
 * PrettyCard object
 */
public class PrettyCard implements Validable {
    /**
     * Button key
     */
    @SerializedName("button")
    private String button;

    /**
     * Button text in current language
     */
    @SerializedName("button_text")
    private String buttonText;

    /**
     * Card ID (long int returned as string)
     */
    @SerializedName("card_id")
    private String cardId;

    @SerializedName("images")
    private List<Image> images;

    /**
     * Link URL
     */
    @SerializedName("link_url")
    private String linkUrl;

    /**
     * Photo ID (format "<owner_id>_<media_id>")
     */
    @SerializedName("photo")
    @Required
    private String photo;

    /**
     * Price if set (decimal number returned as string)
     */
    @SerializedName("price")
    private String price;

    /**
     * Old price if set (decimal number returned as string)
     */
    @SerializedName("price_old")
    private String priceOld;

    /**
     * Title
     */
    @SerializedName("title")
    @Required
    private String title;

    public String getButton() {
        return button;
    }

    public PrettyCard setButton(String button) {
        this.button = button;
        return this;
    }

    public String getButtonText() {
        return buttonText;
    }

    public PrettyCard setButtonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public String getCardId() {
        return cardId;
    }

    public PrettyCard setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public PrettyCard setImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public PrettyCard setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public PrettyCard setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public PrettyCard setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getPriceOld() {
        return priceOld;
    }

    public PrettyCard setPriceOld(String priceOld) {
        this.priceOld = priceOld;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PrettyCard setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(button, buttonText, images, price, priceOld, cardId, linkUrl, photo, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrettyCard prettyCard = (PrettyCard) o;
        return Objects.equals(button, prettyCard.button) &&
                Objects.equals(priceOld, prettyCard.priceOld) &&
                Objects.equals(images, prettyCard.images) &&
                Objects.equals(price, prettyCard.price) &&
                Objects.equals(linkUrl, prettyCard.linkUrl) &&
                Objects.equals(photo, prettyCard.photo) &&
                Objects.equals(buttonText, prettyCard.buttonText) &&
                Objects.equals(title, prettyCard.title) &&
                Objects.equals(cardId, prettyCard.cardId);
    }

    @Override
    public String toString() {
        final Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String toPrettyString() {
        final StringBuilder sb = new StringBuilder("PrettyCard{");
        sb.append("button='").append(button).append("'");
        sb.append(", priceOld='").append(priceOld).append("'");
        sb.append(", images=").append(images);
        sb.append(", price='").append(price).append("'");
        sb.append(", linkUrl='").append(linkUrl).append("'");
        sb.append(", photo='").append(photo).append("'");
        sb.append(", buttonText='").append(buttonText).append("'");
        sb.append(", title='").append(title).append("'");
        sb.append(", cardId='").append(cardId).append("'");
        sb.append('}');
        return sb.toString();
    }
}
