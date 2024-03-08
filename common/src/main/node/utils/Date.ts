import { AxiosResponse } from "axios";

export const convertAPIDateToJSDate = (obj: any) => {
  for (let key in obj) {
    if (typeof obj[key] === "string") {
      const date = Date.parse(obj[key]);

      if (!isNaN(date)) {
        obj[key] = new Date(date);
      }
    } else if (typeof obj[key] === "object") {
      convertAPIDateToJSDate(obj[key]);
    }
  }
};

export const axiosInterceptor = (value: AxiosResponse<any, any>) => {
  if (value.data) {
    convertAPIDateToJSDate(value.data);
  }

  return value;
};
