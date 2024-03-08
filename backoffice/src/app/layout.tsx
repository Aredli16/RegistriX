import "./globals.css";
import React, { ReactNode } from "react";
import LayoutWrapper from "@/components/layout/LayoutWrapper";
import { getServerSession } from "next-auth";
import { getAuthOptions } from "@/lib/auth/AuthOptions";

const Layout = async ({ children }: { children: ReactNode }) => {
  const session = await getServerSession(getAuthOptions());

  return (
    <html lang="en" className="h-full bg-gray-100">
      <body className="h-full">
        <LayoutWrapper session={session}>{children}</LayoutWrapper>
      </body>
    </html>
  );
};
export default Layout;
