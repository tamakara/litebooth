import SHA256 from "crypto-js/sha256.js";
import Base64 from "crypto-js/enc-base64.js";

export default function passwordEncrypt(password) {
    return SHA256(password).toString(Base64)
}