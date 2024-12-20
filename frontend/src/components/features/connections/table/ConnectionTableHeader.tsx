import { TableHead, TableHeader, TableRow } from "@/components/ui/shadcn/table";
import { Connection } from "@/types/Connection";
import { flexRender, Table } from "@tanstack/react-table";

interface ConnectionTableHeaderProps {
  table: Table<Connection>;
}

export default function ConnectionTableHeader({
  table,
}: Readonly<ConnectionTableHeaderProps>) {
  return (
    <TableHeader className="sticky top-0 bg-secondary">
      {table.getHeaderGroups().map((headerGroup) => (
        <TableRow key={headerGroup.id}>
          {headerGroup.headers.map((header) => {
            return (
              <TableHead className="border" key={header.id}>
                {header.isPlaceholder
                  ? null
                  : flexRender(
                      header.column.columnDef.header,
                      header.getContext()
                    )}
              </TableHead>
            );
          })}
        </TableRow>
      ))}
    </TableHeader>
  );
}
