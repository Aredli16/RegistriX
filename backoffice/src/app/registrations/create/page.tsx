import { getServerSession } from "next-auth";
import { redirect } from "next/navigation";
import { getAuthOptions } from "@/lib/auth/AuthOptions";
import { createRegistration, Registration } from "common";
import RegistrationForm from "common/src/main/node/components/registration/RegistrationForm";

const Page = () => {
  const handleSubmit = async (formData: FormData) => {
    "use server";

    const session = await getServerSession(getAuthOptions());

    await createRegistration(
      process.env.API_URL!,
      Object.fromEntries(formData) as Partial<Registration> as Registration,
      session!.access_token,
    );

    redirect("/registrations");
  };

  return (
    <div className="px-4 my-14 sm:px-6 lg:px-8">
      <RegistrationForm onSubmit={handleSubmit} cancelButton />
    </div>
  );
};

export default Page;
