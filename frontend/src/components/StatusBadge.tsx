type Props = {
  status: string;
};

const statusMap: Record<string, { label: string; className: string }> = {
  OPEN: { label: "募集中", className: "bg-green-100 text-green-800" },
  CLOSED: { label: "終了", className: "bg-gray-100 text-gray-600" },
  FILLED: { label: "配属済み", className: "bg-blue-100 text-blue-800" },
};

export default function StatusBadge({ status }: Props) {
  const s = statusMap[status] ?? { label: status, className: "bg-yellow-100 text-yellow-800" };
  return (
    <span className={`inline-block px-2 py-0.5 rounded text-sm font-medium ${s.className}`}>
      {s.label}
    </span>
  );
}
