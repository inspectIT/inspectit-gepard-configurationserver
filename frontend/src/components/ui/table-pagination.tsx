import { Table } from "@tanstack/react-table";
import { Button } from "@/components/ui/shadcn/button";

export default function TablePagination<T>({ table }: { table: Table<T> }) {
  return (
    <div className="flex items-center justify-end space-x-2 py-4">
      <Button
        variant="outline"
        size="sm"
        onClick={() => {
          table.previousPage();
        }}
        disabled={!table.getCanPreviousPage()}
      >
        Previous
      </Button>
      <Button
        variant="outline"
        size="sm"
        onClick={() => {
          table.nextPage();
        }}
        disabled={!table.getCanNextPage()}
      >
        Next
      </Button>
    </div>
  );
}