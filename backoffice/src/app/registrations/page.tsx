import StatCard from "@/components/common/stats/StatCard";
import { FolderIcon } from "@heroicons/react/24/outline";
import RegistrationTable from "@/components/registrations/RegistrationTable";
import { getAllRegistration } from "@/lib/http/api/Registration";

const Page = async ({ searchParams }: { searchParams: { page?: string } }) => {
  const registrationPage = await getAllRegistration(
    searchParams.page &&
      !isNaN(parseInt(searchParams.page)) &&
      !(parseInt(searchParams.page) < 1)
      ? (parseInt(searchParams.page) - 1).toString()
      : "0",
  );

  return (
    <div className="mt-8">
      <div className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
        <h2 className="text-lg font-medium leading-6 text-gray-900">
          Overview
        </h2>

        <div className="mt-2 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
          <StatCard
            name="Total Registrations"
            value={registrationPage.totalElements.toString()}
            icon={
              <FolderIcon
                className="h-6 w-6 text-gray-400"
                aria-hidden="true"
              />
            }
            href="/registrations"
          />
        </div>
      </div>

      <RegistrationTable
        registrationPage={registrationPage}
        currentPage={parseInt(searchParams.page ?? "1")}
      />
    </div>
  );
};

export default Page;
