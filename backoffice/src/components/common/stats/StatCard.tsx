import { ReactElement } from "react";
import Link from "next/link";

const StatCard = ({
  name,
  value,
  icon,
  href,
}: {
  name: string;
  value: string;
  icon: ReactElement;
  href: string;
}) => {
  return (
    <div className="overflow-hidden rounded-lg bg-white shadow">
      <div className="p-5">
        <div className="flex items-center">
          <div className="flex-shrink-0">{icon}</div>
          <div className="ml-5 w-0 flex-1">
            <dl>
              <dt className="truncate text-sm font-medium text-gray-500">
                {name}
              </dt>
              <dd>
                <div className="text-lg font-medium text-gray-900">{value}</div>
              </dd>
            </dl>
          </div>
        </div>
      </div>
      <div className="bg-gray-50 px-5 py-3">
        <div className="text-sm">
          <Link
            href={href}
            className="font-medium text-cyan-700 hover:text-cyan-900"
          >
            View all
          </Link>
        </div>
      </div>
    </div>
  );
};

export default StatCard;
