import { getServerSession } from "next-auth";
import { redirect } from "next/navigation";
import { TrashIcon } from "@heroicons/react/20/solid";
import { getAuthOptions } from "@/lib/auth/AuthOptions";
import {
  deleteRegistrationById,
  getRegistrationById,
  Registration,
  updateRegistration,
} from "common";
import RegistrationForm from "common/src/main/node/components/registration/RegistrationForm";

const Page = async ({ params }: { params: { id: string } }) => {
  const session = await getServerSession(getAuthOptions());
  const registration = await getRegistrationById(
    process.env.API_URL!,
    params.id,
    session!.access_token,
  );

  const handleUpdateSubmit = async (formData: FormData) => {
    "use server";

    await updateRegistration(
      process.env.API_URL!,
      params.id,
      Object.fromEntries(formData) as Partial<Registration> as Registration,
      session!.access_token,
    );

    redirect("/registrations");
  };

  const handleDeleteRegistration = async () => {
    "use server";

    await deleteRegistrationById(
      process.env.API_URL!,
      params.id,
      session!.access_token,
    );

    redirect("/registrations");
  };

  return (
    <div className="px-4 my-14 sm:px-6 lg:px-8">
      <form action={handleDeleteRegistration}>
        <div className="flex justify-end">
          <button
            type="submit"
            className="inline-flex items-center gap-x-1.5 rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-red-600"
          >
            <TrashIcon className="-ml-0.5 h-5 w-5" aria-hidden="true" />
            Delete registration
          </button>
        </div>
      </form>
      <RegistrationForm
        onSubmit={handleUpdateSubmit}
        submitButtonText="Update"
        cancelButton
        defaultRegistration={registration.data}
      />
    </div>
  );
};

export default Page;
