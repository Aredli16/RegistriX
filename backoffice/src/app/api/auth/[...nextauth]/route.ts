import NextAuth from "next-auth";
import { getAuthOptions } from "@/lib/auth/AuthOptions";

const handler = NextAuth(getAuthOptions());

export { handler as GET, handler as POST };
