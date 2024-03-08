import axios from "axios";
import { axiosInterceptor, RegistrationPage } from "common";
import { getServerSession } from "next-auth";
import { getAuthOptions } from "@/lib/auth/AuthOptions";

axios.interceptors.response.use(axiosInterceptor);

export const getAllRegistration = async (
  page: string,
): Promise<RegistrationPage> => {
  const session = await getServerSession(getAuthOptions());

  const response = await axios.get(
    `${process.env.API_URL}/registration?page=${page}`,
    {
      headers: {
        Authorization: `Bearer ${session?.access_token}`,
      },
    },
  );

  if (response.status !== 200) {
    throw new Error("Failed to fetch registration");
  }

  return response.data;
};
