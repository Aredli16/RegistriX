import { JWTPayload } from "jose";

declare module "jwt-decode" {
  export interface JwtPayload extends JWTPayload {
    success: boolean;
  }
}
