import jwt from "jsonwebtoken";
import { getServerSession } from "next-auth";
import { redirect } from "next/navigation";
import DefaultModal from "@/components/common/modals/DefaultModal";
import { jwtDecode } from "jwt-decode";
import { getAuthOptions } from "@/lib/auth/AuthOptions";
import { createRegistration, Registration } from "common";
import RegistrationStep from "@/components/common/registration/RegistrationStep";
import RegistrationForm from "common/src/main/node/components/registration/RegistrationForm";

const Page = ({
  searchParams,
}: {
  searchParams: { token: string | undefined };
}) => {
  let success = true;

  if (searchParams.token) {
    try {
      jwt.verify(searchParams.token!, process.env.NEXTAUTH_SECRET!);

      if (
        jwtDecode(searchParams.token!).success &&
        jwtDecode(searchParams.token!).exp! < Date.now() / 1000
      ) {
        success = true;
      }
    } catch (e) {
      success = false;
    }
  } else {
    success = false;
  }

  const handleSubmit = async (formData: FormData) => {
    "use server";

    const session = await getServerSession(getAuthOptions());

    await createRegistration(
      process.env.API_URL!,
      Object.fromEntries(formData) as Partial<Registration> as Registration,
      session!.access_token,
    );

    redirect(
      "?token=" +
        jwt.sign({ success: true }, process.env.NEXTAUTH_SECRET!, {
          algorithm: "HS256",
          expiresIn: "10s",
        }),
    );
  };

  return (
    <>
      <DefaultModal isOpen={success} />
      <RegistrationStep />
      <div className="mx-auto mt-10 max-w-7xl px-4 sm:px-6 lg:px-8">
        <RegistrationForm onSubmit={handleSubmit} />
      </div>
    </>
  );
};
export default Page;
