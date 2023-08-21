declare module "*.svg" {
    import { ReactElement, SVGProps } from "react";
    const content: (props: SVGProps<SVGSVGElement>) => ReactElement;
    export default content;
}
